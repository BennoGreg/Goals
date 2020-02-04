package at.fhooe.mc.goals.ui.statistics

import at.fhooe.mc.goals.Database.StatisticData

object StatisticsSingleton {

    var stats: StatisticData? = null


    fun updateNrOfGoals(period: Int, addNumber: Int) {

        val temp = stats

        if (temp != null) {
            when (period) {

                0 -> temp.nrOfTotalDaily += addNumber
                1 -> temp.nrOfTotalWeekly += addNumber
                2 -> temp.nrOfTotalMonthly += addNumber
                3 -> temp.nrOfTotalYearly += addNumber
            }
            if (addNumber != -1) temp.nrOfTotal += addNumber
        }

    }

    fun updateAchieved(period: Int, addNumber: Int) {

        val temp = stats

        if (temp != null) {

            when (period) {
                0 -> temp.nrOfAchievedDaily += addNumber
                1 -> temp.nrOfAchievedWeekly += addNumber
                2 -> temp.nrOfAchievedMonthly += addNumber
                3 -> temp.nrOfAchievedYearly += addNumber
            }


            if (addNumber != -1) temp.nrOfTotalAchieved += addNumber
        }
    }

    fun resetAchieved(period: Int) {
        val temp = stats

        if (temp != null) {

            when (period) {
                0 -> temp.nrOfAchievedDaily = 0
                1 -> temp.nrOfAchievedWeekly = 0
                2 -> temp.nrOfAchievedMonthly = 0
                3 -> temp.nrOfAchievedYearly = 0
            }
        }
    }

    fun updateTotal() {
        val temp = stats
        if (temp != null) {
            temp.nrOfTotal += 1
        }
    }

    fun updateAfterEdit(
        oldPeriod: Int,
        newPeriod: Int,
        isAchieved: Boolean,
        isEqualFrequency: Boolean
    ) {

        val temp = stats
        if (temp != null) {
            when (oldPeriod) {
                0 -> {
                    if (isAchieved) temp.nrOfAchievedDaily -= 1
                    temp.nrOfTotalDaily -= 1
                }
                1 -> {
                    if (isAchieved) temp.nrOfAchievedWeekly -= 1
                    temp.nrOfTotalWeekly -= 1
                }
                2 -> {
                    if (isAchieved) temp.nrOfAchievedMonthly -= 1
                    temp.nrOfTotalMonthly -= 1
                }
                3 -> {
                    if (isAchieved) temp.nrOfAchievedYearly -= 1
                    temp.nrOfTotalYearly -= 1
                }
            }

            when (newPeriod) {
                0 -> {
                    if (isAchieved && isEqualFrequency) temp.nrOfAchievedDaily += 1
                    temp.nrOfTotalDaily += 1
                }
                1 -> {
                    if (isAchieved && isEqualFrequency) temp.nrOfAchievedWeekly += 1
                    temp.nrOfTotalWeekly += 1
                }
                2 -> {
                    if (isAchieved && isEqualFrequency) temp.nrOfAchievedMonthly += 1
                    temp.nrOfTotalMonthly += 1
                }
                3 -> {
                    if (isAchieved && isEqualFrequency) temp.nrOfAchievedYearly += 1
                    temp.nrOfTotalYearly = 1
                }
            }
            if (isAchieved && !isEqualFrequency) temp.nrOfTotalAchieved -= 1
        }
    }
}