package ru.qa.template.tests;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {
    @Test
    public void testGbrIp() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("212.58.246.79");
        assertEquals(geoIP.getCountryCode(), "GBR");
    }
}
