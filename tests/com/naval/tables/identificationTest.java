package com.naval.tables;

import com.naval.modele.Navire;
import com.naval.outils.Distance;
import java.io.IOException;
import junit.framework.Assert;
import org.junit.Test;

public class identificationTest {
		
	@Test public void horizonVisuel() throws IOException {
            
//            String content = "GameOne\n90\n17\n52\n2\n0#chris\n1#francis\n" +
//                             "2\n0#Nagato#JP#JAP1#12.62#10.79#25#45#CL#1\n"+
//                    "1#Idaho#US#US1#32.65#24.06#19.5#220#FM#1\n" ;
//            
//            Partie partie = Partie.creer(new StringReader(content));
            
            // detection
            Assert.assertEquals("T1 -> T1", (float) 40.0, TableHorizonVisuel.getHV(1, 1));
            Assert.assertEquals("T2 -> T2", (float) 30.0, TableHorizonVisuel.getHV(2, 2));
            Assert.assertEquals("T6 -> T4", (float) 15.0, TableHorizonVisuel.getHV(6, 4));
	}
        
        @Test public void seuil() throws IOException {
            
            Navire nA = new Navire("0#Nagato#JP#JAP1#12.62#10.79#25#45#CL#1");
            Navire nB = new Navire("1#Idaho#US#US1#32.65#24.06#19.5#220#FM#1");
            
            Assert.assertFalse("T1 vs T1", TableHorizonVisuel.visible(90, nA, nB));
        }
}
