package ui

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

fun WCRenderScope.menuBackground(key: Any = AutoKey,
                                 size: Vector) =
		rectFill(key = key,
		         bounds = Bounds(size = size),
		         fillStyle = Color.hsla(0, 0.0, 0.0, 0.6))

fun WCRenderScope.menuCenter(key: Any = AutoKey,
                             size: Vector,
                             content: WCRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = (size / 2.0)!!,
		          content = content)

fun WCRenderScope.menuBottomRight(key: Any = AutoKey,
                                          size: Vector,
                                          content: WCRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = Vector(x = size.x, y = size.y),
		          content = content)
