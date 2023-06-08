package step3

import step3.domain.car.CarFactory
import step3.domain.game.RaceGame
import step3.view.CarPositionResult
import step3.view.InputView
import step3.view.ResultView
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val (carNames, round) = InputView.readStartInput()

    val cars = CarFactory.createCars(carNames = carNames)

    val raceGame = RaceGame(cars = cars, round = AtomicInteger(round))
    ResultView.printStartGame()

    while (raceGame.isProgress()) {
        val raceGameResult = raceGame.basicFormulaRace()

        ResultView.printCarPositionResults(
            results = raceGameResult.map {
                CarPositionResult(
                    name = it.name,
                    position = it.position,
                )
            }
        )
    }

    ResultView.printFinalHeadOfRace(
        headOfRaceCarNames = raceGame.currentHeadOfRace(),
    )
}
