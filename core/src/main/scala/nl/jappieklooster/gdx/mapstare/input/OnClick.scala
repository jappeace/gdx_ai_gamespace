package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


class OnClick(function: (InputEvent, Float,Float) => Unit) extends ClickListener{
	override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
		function(event,x,y)
	}
}
object OnClick{
	def apply(function:()=>Unit):OnClick=apply((a, b,c)=>function())
	def apply(function: (InputEvent, Float,Float) => Unit)=new OnClick(function)
}
