package nl.jappieklooster.gdx.mapstare.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui._

/**
  * share skin and stylename across ui elements
  */
class UIFactory(skin:Skin = new Skin(Gdx.files.internal("uiskin.json")), styleName:String = "default") {
	def table() = new Table(skin)
	def scrollPane() = {
		val scrolltable = table()
		val scrollpane = new ScrollPane(scrolltable, skin, styleName)
		(scrolltable, scrollpane)
	}
	def button(text:String) = new TextButton(text, skin, styleName)
	def textField(value:String) = new TextField(value, skin)
	def dialog(title:String) = new Dialog(title, skin)

}
