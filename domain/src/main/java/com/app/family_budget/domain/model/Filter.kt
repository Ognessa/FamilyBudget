package com.app.family_budget.domain.model

import com.app.family_budget.domain.R


enum class Filter(val filterNameRes: Int) {
    LastMonth(R.string.last_month),
    LastWeek(R.string.last_week),
    LastDay(R.string.last_day)
}

