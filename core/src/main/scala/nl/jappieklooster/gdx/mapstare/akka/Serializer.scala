package nl.jappieklooster.gdx.mapstare.akka

import java.io._

import akka.util.ByteString

object Serializer {
	def serialize[T <: Serializable](obj: T): ByteString = {
		val byteOut = new ByteArrayOutputStream()
		val objOut = new ObjectOutputStream(byteOut)
		objOut.writeObject(obj)
		objOut.close()
		byteOut.close()
		ByteString(byteOut.toByteArray)
	}
	def deserialize[T <: Serializable](bytes: ByteString): T = {
		val byteIn = new ByteArrayInputStream(bytes.toArray)
		val objIn = new ObjectInputStream(byteIn)
		val obj = objIn.readObject().asInstanceOf[T]
		byteIn.close()
		objIn.close()
		obj
	}
}
