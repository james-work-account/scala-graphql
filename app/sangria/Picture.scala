package sangria

import sangria.macros.derive._

case class Picture(width: Int, height: Int, url: Option[String])

object Picture {
  implicit val PictureType =
    deriveObjectType[Unit, Picture](
      ObjectTypeDescription("The product picture"),
      DocumentField("url", "Picture CDN URL"))
}
