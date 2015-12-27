package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


/**
 * A less verbose click listener
 * @param function
 */
class OnClick(function: (InputEvent, Float,Float) => Unit) extends ClickListener{
	override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
		function(event,x,y)
	}
}
object OnClick{
	/**
	 * the allows passing of a function that doesn't care about arguments
	 * (most common use case probably)
	 * @param function
	 * @return
	 */
	def apply(function:()=>Unit):OnClick=apply((a, b,c)=>function())
	def apply(function:(InputEvent)=>Unit):OnClick=apply((a, b,c)=>function(a))
	def apply(function: (InputEvent, Float,Float) => Unit)=new OnClick(function)
}
