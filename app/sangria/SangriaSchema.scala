package sangria

import com.google.inject.Inject
import sangria.schema._

class SangriaSchema @Inject()(implicit val repo: Repo){
  val Id = Argument("id", StringType)

  val QueryType = ObjectType("Query", fields[Repo, Unit](
    Field("product", OptionType(Product.ProductType),
      description = Some("Returns a product with specific `id`."),
      arguments = Id :: Nil,
      resolve = c => c.ctx.product(c arg Id)
    ),
    Field("products", ListType(Product.ProductType),
      description = Some("Returns a list of all available products."),
      resolve = _.ctx.products
    ),
    Field("owner", OptionType(Owner.OwnerType),
      description = Some("Returns an owner with specific `id`."),
      arguments = Id :: Nil,
      resolve = c => c.ctx.owner(c arg Id)
    ),
    Field("owners", ListType(Owner.OwnerType),
      description = Some("Returns a list of all available owners."),
      resolve = _.ctx.owners
    )
  ))

  val schema = Schema(QueryType)


}
