package sangria

import sangria.macros.derive._
import sangria.schema.{Field, ListType, ObjectType, OptionType}

case class Owner(id: String, name: String, isHeavy: Boolean, products: List[String]) extends Identifiable

object Owner {
  def OwnerType(implicit repo: Repo): ObjectType[Unit, Owner] =
    deriveObjectType[Unit, Owner](
      Interfaces(Identifiable.IdentifiableType),
      ReplaceField("products", Field("products", Product.ListProductType, resolve = c => repo.products.filter(_.ownerId.getOrElse("") == c.value.id))))

  def OptOwnerType(implicit repo: Repo): OptionType[Owner] =
    OptionType(
      OwnerType
    )
}
