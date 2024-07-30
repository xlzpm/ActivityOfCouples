package com.example.randomActivities.data.repository

import com.example.network.domain.repository.RandomActivityRepository
import com.example.randomActivities.domain.dao.RandomActivitiesDao
import com.example.randomActivities.domain.model.RandomActivitiesEntity
import com.example.randomActivities.domain.model.toEntity
import com.example.randomActivities.domain.repository.RandomActivitiesDbRepository

class RandomActivitiesDbRepositoryImpl(
    private val randomActivityDao: RandomActivitiesDao,
    private val randomActivityRepository: RandomActivityRepository
): RandomActivitiesDbRepository {
    override suspend fun getRandomActivities(): List<RandomActivitiesEntity>? {
        val result = randomActivityDao.getRandomActivities()
        if (result.isEmpty()){
            return null
        }
        return result
    }

    override suspend fun getRandomActivity(): RandomActivitiesEntity {
        val randomActivityEntity = randomActivityRepository.getRandomActivity().toEntity()
        randomActivityDao.addRandomActivity(randomActivityEntity = randomActivityEntity)
        return randomActivityEntity
    }
}
