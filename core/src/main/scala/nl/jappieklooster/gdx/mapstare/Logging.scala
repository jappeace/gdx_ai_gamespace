package nl.jappieklooster.gdx.mapstare

import org.slf4j.LoggerFactory

/**
  * Make a class have a *log* attribute, which allows the class to sends
  * its worlds worries to that attribute
  */
trait Logging {
	val log = LoggerFactory.getLogger(this.getClass)
}
