package com.itd.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.itd.myapp.web.rest.TestUtil;

public class OSVersionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OSVersion.class);
        OSVersion oSVersion1 = new OSVersion();
        oSVersion1.setId(1L);
        OSVersion oSVersion2 = new OSVersion();
        oSVersion2.setId(oSVersion1.getId());
        assertThat(oSVersion1).isEqualTo(oSVersion2);
        oSVersion2.setId(2L);
        assertThat(oSVersion1).isNotEqualTo(oSVersion2);
        oSVersion1.setId(null);
        assertThat(oSVersion1).isNotEqualTo(oSVersion2);
    }
}
