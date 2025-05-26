package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.external.OtherInfoService
import ayds.songinfo.moredetails.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class OtherInfoRepositoryTest{
    private val otherInfoLocalStorage: OtherInfoLocalStorage = mockk(relaxUnitFun =  true )
    private val otherInfoService: OtherInfoService = mockk(relaxUnitFun =  true )
    private val otherInfoRepository: OtherInfoRepository = OtherInfoRepositoryImpl(otherInfoLocalStorage, otherInfoService)


    @Test
    fun `on getArtistInfo call getArticle from local storage`() {
        val artistBiography = ArtistBiography("artist", "biography", "url")
        every { otherInfoLocalStorage.getArticle("artist") } returns artistBiography

        val result = otherInfoRepository.getArtistInfo("artist")

        Assert.assertEquals(artistBiography.artistName, result.artistName)

    }

    @Test
    fun `on non local stored artist should return service artist`() {
        val artistBiography = ArtistBiography("artist", "biography", "url")
        every { otherInfoLocalStorage.getArticle("artist") } returns null
        every { otherInfoService.getArticle("artist") } returns artistBiography

        val result = otherInfoRepository.getArtistInfo("artist")
        Assert.assertEquals(artistBiography, result)
        verify { otherInfoLocalStorage.insertArtist(artistBiography) }
    }

    @Test
    fun `on non local stored artist should return service artist on empty biography`() {
        val artistBiography = ArtistBiography("artist", "", "url")
        every { otherInfoLocalStorage.getArticle("artist") } returns null
        every { otherInfoService.getArticle("artist") } returns artistBiography

        val result = otherInfoRepository.getArtistInfo("artist")
        Assert.assertEquals(artistBiography, result)
        verify(inverse = true) { otherInfoLocalStorage.insertArtist(artistBiography) }
    }
}
