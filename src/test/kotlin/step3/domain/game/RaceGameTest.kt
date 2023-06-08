package step3.domain.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import step3.model.RaceGameErrorCode

class RaceGameTest : DescribeSpec({

    describe(name = "레이스 게임 시작할 때") {
        context(name = "자동차 개수가 지정된 개수보다 낮으면") {
            val exception = shouldThrow<IllegalArgumentException> {
                RaceGame(2(carCount), 1(round))
            }

            it(name = "시작할 수 없다는 에러가 발생한다.") {
                exception shouldHaveMessage RaceGameErrorCode.INVALID_CAR_COUNT.message("2 1")
            }
        }
    }

    describe(name = "레이스 게임 시작한 후") {
        context(name = "라운드가 모두 소진되었으면") {
            val raceGame = RaceGame(3, 1)

            val exception = shouldThrow<IllegalStateException> {
                raceGame.race()
            }

            it(name = "게임을 시작하면 남은 라운드 수가 없다는 에러가 발생한다.") {
                exception shouldHaveMessage RaceGameErrorCode.NOT_REMAINING_ROUND.message("1")
            }

            val progress = raceGame.isProgress()

            it(name = "진행 여부를 호출하면 진행 중이지 않음을 반환한다.") {
                progress shouldBe false
            }
        }

        context(name = "라운드가 남아있는 상태에서") {
            val raceGame = RaceGame(3, 10)

            val currentPosition = raceGame.race()

            it(name = " 게임을 시작하면 자동차 숫자만큼 현재 위치를 반환한다.") {
                currentPosition.size shouldBe 3
            }

            val progress = raceGame.isProgress()

            it(name = "진행 여부를 호출하면 진행 중임을 반환한다.") {
                progress shouldBe true
            }
        }
    }
})
