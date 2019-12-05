package com.itd.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.itd.myapp.web.rest.TestUtil;

public class ComponentMasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComponentMaster.class);
        ComponentMaster componentMaster1 = new ComponentMaster();
        componentMaster1.setId(1L);
        ComponentMaster componentMaster2 = new ComponentMaster();
        componentMaster2.setId(componentMaster1.getId());
        assertThat(componentMaster1).isEqualTo(componentMaster2);
        componentMaster2.setId(2L);
        assertThat(componentMaster1).isNotEqualTo(componentMaster2);
        componentMaster1.setId(null);
        assertThat(componentMaster1).isNotEqualTo(componentMaster2);
    }
}
