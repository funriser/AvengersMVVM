package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.entity.Avenger
import com.google.android.gms.common.util.ThreadUtils
import com.google.android.gms.tasks.Tasks
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(ThreadUtils::class)
class AvengersRepositoryTest {

    private lateinit var repository: AvengersRepositoryImpl
    private lateinit var firestore: Firestore

    @Before
    fun setUp () {
        PowerMockito.mockStatic(ThreadUtils::class.java)
        firestore = mock()
        repository = AvengersRepositoryImpl(firestore)
    }

    @Test
    fun `Getting avengers from network` () {
        val testAvengers = listOf(Avenger("Tony", 50, "Iron Man"))

        whenever(firestore.getAvengers()).thenReturn(Tasks.forResult(testAvengers))
        whenever(ThreadUtils.isMainThread()).thenReturn(false)

        val avengersResult = repository.avengers()
        assertEquals(avengersResult, Either.Right(testAvengers))
        verify(firestore).getAvengers()
    }

}