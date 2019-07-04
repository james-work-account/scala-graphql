package sangria

class Repo {
  private val Products = List(
    Product("1", "Cheesecake", "Tasty", Some("1")),
    Product("2", "Health Potion", "+50 HP", Some("2")),
    Product("3", "Beef", "Is Beef", Some("1")),
    Product("4", "Mystery Food", "???", None))
  private val Owners = List(
    Owner("1", "Big Boy", true, List("1")),
    Owner("2", "Lil Boy", false, List("2")))

  def product(id: String): Option[Product] = Products find (_.id == id)

  def products: List[Product] = Products

  def owner(id: String): Option[Owner] = Owners find (_.id == id)

  def owners: List[Owner] = Owners
}