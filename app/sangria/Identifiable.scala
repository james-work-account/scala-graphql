package sangria

import sangria.schema._

trait Identifiable {
  def id: String
}

object Identifiable {
  val IdentifiableType = InterfaceType(
    "Identifiable",
    "Entity that can be identified",
    fields[Unit, Identifiable](
      Field("id", StringType, resolve = _.value.id)))
}
