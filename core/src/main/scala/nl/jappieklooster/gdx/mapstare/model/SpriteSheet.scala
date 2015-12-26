package nl.jappieklooster.gdx.mapstare.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

case class SpriteSheet(frameCount:Int, frameWidth:Int, frameHeight:Int, textureName:String) {
	lazy val texture = new Texture(Gdx.files.internal(textureName))

}
