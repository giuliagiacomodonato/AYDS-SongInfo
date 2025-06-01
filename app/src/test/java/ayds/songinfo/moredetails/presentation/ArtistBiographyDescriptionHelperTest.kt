package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class ArtistBiographyDescriptionHelperTest{

    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()


    @Test
    fun `on localy stored artist should return ArtistBiography`() {
        val artistBiography = ArtistBiography("artist", "biography", "url", true)
        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        Assert.assertEquals( "<html><div width=400><font face=\"arial\">[*]biography</font></div></html>", result)
    }

    @Test
    fun `on non local storage artist should return ArtistBiography`() {
        val artistBiography = ArtistBiography("artist", "biography", "url", false)
        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)
        Assert.assertEquals( "<html><div width=400><font face=\"arial\">biography</font></div></html>", result)
    }
    @Test
    fun `should remove apostrophes`() {
        val artistBiography = ArtistBiography("artist", "biography'n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography n</font></div></html>",
            result
        )
    }

    @Test
    fun `should fix on double slash`() {
        val artistBiography = ArtistBiography("artist", "biography\\n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }

    @Test
    fun `should map break lines`() {
        val artistBiography = ArtistBiography("artist", "biography\n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }
    @Test
    fun `should set artist name bold`() {
        val artistBiography = ArtistBiography("artist", "biography artist", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography <b>ARTIST</b></font></div></html>",
            result
        )
    }
}