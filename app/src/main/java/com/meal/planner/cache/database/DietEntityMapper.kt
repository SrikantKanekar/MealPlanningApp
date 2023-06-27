package com.meal.planner.cache.database

import com.meal.planner.model.Diet

fun dietMapper(obj: DietEntity): Diet {
    return Diet(
        id = obj.id,
        name = obj.name,
        meals = ArrayList(obj.meals),
        daysOfWeek = obj.daysOfWeek
    )
}

fun dietEntityMapper(model: Diet): DietEntity {
    return DietEntity(
        id = model.id,
        name = model.name,
        meals = model.meals,
        daysOfWeek = model.daysOfWeek
    )
}
