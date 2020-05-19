package screens.game

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.URenderBuilder
import pl.karol202.uranium.core.render.render
import pl.karol202.uranium.webcanvas.WC
import pl.karol202.uranium.webcanvas.WCElement
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.event.eventHandler
import pl.karol202.uranium.webcanvas.values.InputEvent
import pl.karol202.uranium.webcanvas.values.InputEvent.Mouse.Type
import pl.karol202.uranium.webcanvas.values.Vector

class MouseFollower(props: Props) : WCAbstractComponent<MouseFollower.Props>(props)
{
	data class Props(override val key: Any,
	                 val minX: Double,
	                 val maxX: Double,
	                 val currentX: Double,
	                 val onMove: (Double) -> Unit,
	                 val content: List<WCElement<*>>) : UProps

	override fun URenderBuilder<WC>.render()
	{
		+ eventHandler(mouseListener = { handleEvent(it) })
		+ translate(vector = Vector(x = props.currentX)) {
			+ props.content
		}
	}

	private fun handleEvent(event: InputEvent.Mouse) =
			if(event.type == Type.MOVE) props.onMove(event.location.x.coerceIn(props.minX, props.maxX))
			else Unit
}

fun WCRenderScope.mouseFollower(key: Any = AutoKey,
                                minX: Double = Double.NEGATIVE_INFINITY,
                                maxX: Double = Double.POSITIVE_INFINITY,
                                currentX: Double,
                                onMove: (Double) -> Unit,
                                content: WCRenderBuilder.() -> Unit) =
		component(::MouseFollower, MouseFollower.Props(key, minX, maxX, currentX, onMove, content.render()))
