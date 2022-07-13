package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MercatorProjectorTest {

    static final float ACCURACY = (float) 1/10000;

    /**
     * Tests of conversion geo coordinate -> x/y coordinate
     * With center 0/0
     */
    @Test
    public void test1() {
        MercatorProjector p = new MercatorProjector(0, 0);

        Assertions.assertEquals(0.15969762655748115, p.getX(9.15), ACCURACY);
        Assertions.assertEquals(0.978497869120544, p.getY(48.8), ACCURACY);
    }

    /**
     * Tests of conversion geo coordinate -> x/y coordinate
     * With center Kassel (DE)
     */
    @Test
    public void test2() {
        MercatorProjector p = new MercatorProjector(51.309929, 9.470913);
        //TODO: Adjust values
        Assertions.assertEquals(0.15969762655748115, p.getX(9.15), ACCURACY);
        Assertions.assertEquals(0.978497869120544, p.getY(48.8), ACCURACY);
    }

    /**
     * Tests of conversion x/y coordinate -> geo coordinate
     * With center 0/0
     */
    @Test
    public void test3() {
        MercatorProjector p = new MercatorProjector(0, 0);

        Assertions.assertEquals(9.15, p.getLongitude(0.15969762655748115), ACCURACY);
        Assertions.assertEquals(48.8, p.getLatitude(0.978497869120544), ACCURACY);
    }

}
