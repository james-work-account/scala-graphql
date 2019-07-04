package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.parser.{QueryParser, SyntaxError}
import sangria.{Repo, SangriaSchema}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

@Singleton
class GraphQLController @Inject()(cc: ControllerComponents, implicit val sangriaSchema: SangriaSchema) extends AbstractController(cc) {

  def graphQl: Action[JsValue] = Action.async(parse.json) { implicit request =>
    val query = (request.body \ "query").as[String]
    val operation = (request.body \ "operationName").asOpt[String]
    val variables: JsObject = {
      (request.body \ "variables").toOption.map {
        case JsString(vars) => parseVariables(vars)
        case obj: JsObject => obj
        case _ => Json.obj()
      }
    }.getOrElse(Json.obj())

    QueryParser.parse(query) match {
      // query parsed successfully, time to execute it!
      case Success(queryAst) =>
        executeGraphQLQuery(queryAst, operation, variables)

      // can't parse GraphQL query, return error
      case Failure(error: SyntaxError) =>
        Future.successful(BadRequest(Json.obj("error" → error.getMessage)))
    }
  }

  private def parseVariables(variables: String) =
    if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]

  import sangria.marshalling.playJson._

  private def executeGraphQLQuery(query: Document, op: Option[String], vars: JsObject) =
    Executor.execute(sangriaSchema.schema, query, new Repo, operationName = op, variables = vars)
      .map(Ok(_))
      .recover {
        case error: QueryAnalysisError ⇒ BadRequest(error.resolveError)
        case error: ErrorWithResolver ⇒ InternalServerError(error.resolveError)
      }


}