import mill._, scalalib._

trait Common extends ScalaModule {
  def scalaVersion = "3.1.1"
  def ammoniteVersion = "2.5.1-7-cd989427"
}

val gdxVersion = "1.10.0"
val box2DLightsVersion="1.5"
val roboVMVersion = "2.3.15"

object drop extends ScalaModule {
  def scalaVersion = "3.1.1"
  object desktop extends ScalaModule {
    def scalaVersion = "3.1.1"
    def moduleDeps = Seq(
      core,
      drop
    )
    def ivyDeps = Agg(
      ivy"com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion",
      ivy"com.badlogicgames.gdx:gdx-platform:$gdxVersion;classifier=natives-desktop",
      ivy"com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion;classifier=natives-desktop"
    )
  }

  object core extends ScalaModule {
    def scalaVersion = "3.1.1"
    def moduleDeps = Seq(
      drop
    )
    def ivyDeps = Agg(
      ivy"com.badlogicgames.gdx:gdx:$gdxVersion",
      ivy"com.badlogicgames.gdx:gdx-box2d:$gdxVersion",
      ivy"com.badlogicgames.box2dlights:box2dlights:$box2DLightsVersion"
    )
  }
}
