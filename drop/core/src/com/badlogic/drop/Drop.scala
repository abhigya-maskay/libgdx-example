package com.badlogic.drop

import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import com.badlogic.gdx.audio.{Music, Sound}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.TimeUtils

class Drop extends ApplicationAdapter {
  private var dropImage: Texture  = null
  private var bucketImage: Texture = null
  private var dropSound: Sound = null
  private var rainMusic: Music = null
  private var camera : OrthographicCamera = null
  private var batch : SpriteBatch = null
  private var bucket: Rectangle = null
  private val touchPos = new Vector3
  private var raindrops: Array[Rectangle] = null
  private var lastDropTime = 0L

  override def create() =
    dropImage = new Texture(Gdx.files.internal("droplet.png"))
    bucketImage = new Texture(Gdx.files.internal("bucket.png"))

    dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"))
    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"))

    rainMusic.setLooping(true)
    rainMusic.play()

    camera = new OrthographicCamera
    camera.setToOrtho(false, 800, 480)

    batch = new SpriteBatch

    bucket = new Rectangle
    bucket.x = 800/2 - 64/2
    bucket.y = 20
    bucket.width = 64
    bucket.height = 64

    raindrops = new Array[Rectangle]
    spawnRaindrop

  override def render() =
    ScreenUtils.clear(0,0,0.2f, 1)
    camera.update
    batch.setProjectionMatrix(camera.combined)
    batch.begin
    batch.draw(bucketImage, bucket.x, bucket.y)
    raindrops.forEach(
      raindrop => batch.draw(dropImage, raindrop.x, raindrop.y)
    )
    batch.`end`
    if(Gdx.input.isTouched()) then
      touchPos.set(Gdx.input.getX, Gdx.input.getY, 0)
      camera.unproject(touchPos)
      bucket.x = touchPos.x - 64/2

    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) then
      bucket.x -= 200 * Gdx.graphics.getDeltaTime
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) then
      bucket.x += 200 * Gdx.graphics.getDeltaTime
    if (bucket.x < 0) then
      bucket.x = 0
    if (bucket.x > 800 - 64) then
      bucket.x = 800 - 64

    if(TimeUtils.nanoTime - lastDropTime > 1000000000) then
      spawnRaindrop

    val iter = raindrops.iterator
    while (iter.hasNext) {
      val raindrop = iter.next()
      raindrop.y -= 200 * Gdx.graphics.getDeltaTime
      if (raindrop.y + 64 < 0) then
        iter.remove
      if (raindrop.overlaps(bucket)) then
        dropSound.play()
        iter.remove
    }


  private def spawnRaindrop =
    val raindrop = new Rectangle
    raindrop.x = MathUtils.random(0, 800-64)
    raindrop.y = 480
    raindrop.width = 64
    raindrop.height = 64
    raindrops.add(raindrop)
    lastDropTime = TimeUtils.nanoTime
}
