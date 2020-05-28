package screens.game

import pl.karol202.uranium.core.common.UState
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.values.Vector

sealed class GameState : UState
{
	data class Prepare(override val screenSize: Vector,
	                   override val level: Level,
	                   override val paddleX: Double,
	                   override val bricks: List<Brick>,
	                   override val playerHp: Int) : GameState()
	{
		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun play(ballState: WCRigidbody.State) = Play(screenSize, level, paddleX, bricks, playerHp, ballState)
	}

	data class Play(override val screenSize: Vector,
	                override val level: Level,
	                override val paddleX: Double,
	                override val bricks: List<Brick>,
	                override val playerHp: Int,
	                val ballState: WCRigidbody.State) : GameState()
	{
		private val isLevelCompleted get() = level.isCompleted(bricks)

		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun withBrickHit(brickId: String): GameState
		{
			val newState = copy(bricks = bricks.mapNotNull { if(it.id == brickId) it.hit() else it })
			return if(newState.isLevelCompleted) newState.withNextLevel()
			else newState
		}

		fun withPlayerHpDecremented() = if(playerHp > 1) copy(playerHp = playerHp - 1).prepare() else gameOver()

		fun withBallState(ballState: WCRigidbody.State) = copy(ballState = ballState)

		private fun withNextLevel(): GameState
		{
			val nextLevel = level.nextLevel
			return if(nextLevel == Level.WinLevel) win()
			else copy(level = nextLevel,
			          bricks = nextLevel.generateBricks(screenSize),
			          playerHp = nextLevel.initialHp ?: playerHp).prepare()
		}

		private fun prepare() = Prepare(screenSize, level, paddleX, bricks, playerHp)

		private fun win() = Win(screenSize, level, paddleX, playerHp)

		private fun gameOver() = GameOver(screenSize, level, paddleX, bricks)
	}

	data class Win(override val screenSize: Vector,
	               override val level: Level,
	               override val paddleX: Double,
	               override val playerHp: Int) : GameState()
	{
		override val bricks get() = emptyList<Brick>()

		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)
	}

	data class GameOver(override val screenSize: Vector,
	                    override val level: Level,
	                    override val paddleX: Double,
	                    override val bricks: List<Brick>) : GameState()
	{
		override val playerHp get() = 0

		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)
	}

	companion object
	{
		fun initial(size: Vector, level: Level): GameState = Prepare(screenSize = size,
		                                                             level = level,
		                                                             paddleX = size.x / 2.0,
		                                                             bricks = level.generateBricks(size),
		                                                             playerHp = level.initialHp ?: 1)
	}

	abstract val screenSize: Vector
	abstract val level: Level
	abstract val paddleX: Double
	abstract val bricks: List<Brick>
	abstract val playerHp: Int

	abstract fun withPaddleX(paddleX: Double): GameState
}
