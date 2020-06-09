package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeElement
import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderBuilder
import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.component.base.ArkadeAbstractComponent
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.translate
import pl.karol202.uranium.arkade.htmlcanvas.component.event.eventHandler
import pl.karol202.uranium.arkade.htmlcanvas.values.InputEvent
import pl.karol202.uranium.arkade.htmlcanvas.values.InputEvent.Mouse.Type
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.common.UProps
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.render

class MouseFollower(props: Props) : ArkadeAbstractComponent<MouseFollower.Props>(props)
{
	data class Props(override val key: Any,
	                 val minX: Double,
	                 val maxX: Double,
	                 val currentX: Double,
	                 val onMove: (Double) -> Unit,
	                 val content: List<ArkadeElement<*>>) : UProps

	override fun ArkadeRenderBuilder.render()
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

fun ArkadeRenderScope.mouseFollower(key: Any = AutoKey,
                                minX: Double = Double.NEGATIVE_INFINITY,
                                maxX: Double = Double.POSITIVE_INFINITY,
                                currentX: Double,
                                onMove: (Double) -> Unit,
                                content: ArkadeRenderBuilder.() -> Unit) =
		component(::MouseFollower, MouseFollower.Props(key, minX, maxX, currentX, onMove, content.render()))
