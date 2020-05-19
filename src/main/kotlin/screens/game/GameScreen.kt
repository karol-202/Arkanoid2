package screens.game

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.render
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.scale
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.event.eventHandler
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.physics.collider.collisionDomain
import pl.karol202.uranium.webcanvas.component.physics.rigidbody
import pl.karol202.uranium.webcanvas.component.primitives.canvasFill
import pl.karol202.uranium.webcanvas.component.primitives.circleFill
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.physics.Collision
import pl.karol202.uranium.webcanvas.physics.collider.CircleCollider
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.*

class GameScreen(props: Props) : WCAbstractComponent<GameScreen.Props>(props),
                                 UStateful<GameState>
{
	data class Props(override val key: Any,
	                 val size: Vector) : UProps

	override var state by state(GameState.initial(props.size))

	override fun WCRenderBuilder.render()
	{
		val state = state

		+ canvasFill(fillStyle = Color.raw("black"))
		+ when(state)
		{
			is GameState.Prepare -> prepareStateComponent(size = props.size,
			                                              gameState = state,
			                                              onPaddlePositionChange = ::setPaddleX,
			                                              onStart = ::startGame)
			is GameState.Play -> playStateComponent(size = props.size,
			                                        gameState = state,
			                                        onPaddlePositionChange = ::setPaddleX,
			                                        onBallStateChange = ::setBallState,
			                                        onDeath = ::endGame)
		}
	}

	private fun setPaddleX(paddleX: Double) = setState { withPaddleX(paddleX) }

	private fun startGame() = setState {
		if(this is GameState.Prepare) play(ballState = createBallState(props.size, paddleX))
		else this
	}

	private fun setBallState(ballState: WCRigidbody.State) = setState {
		if(this is GameState.Play) withBallState(ballState)
		else this
	}

	private fun endGame() = setState {
		if(this is GameState.Play) prepare()
		else this
	}
}

fun WCRenderScope.gameScreen(key: Any = AutoKey,
                             size: Vector) = component(::GameScreen, GameScreen.Props(key, size))
