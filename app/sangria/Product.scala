package sangria

import sangria.macros.derive._
import sangria.schema.{Field, ListType, ObjectType}

case class Product(id: String, name: String, description: String, ownerId: Option[String]) extends Identifiable {
  def reversedName: String = name.reverse
}

object Product {

  def ProductType(implicit repo: Repo): ObjectType[Unit, Product] =
    deriveObjectType[Unit, Product](
      Interfaces(Identifiable.IdentifiableType),
      IncludeMethods("reversedName"),
      ReplaceField("ownerId", Field("owner", Owner.OptOwnerType, resolve = c => repo.owners.find(_.id == c.value.ownerId.getOrElse("")))))

  def ListProductType(implicit repo: Repo): ListType[Product] =
    ListType(
      ProductType
    )
}
