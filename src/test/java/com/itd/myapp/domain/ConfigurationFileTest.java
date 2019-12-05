package com.itd.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.itd.myapp.web.rest.TestUtil;

public class ConfigurationFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigurationFile.class);
        ConfigurationFile configurationFile1 = new ConfigurationFile();
        configurationFile1.setId(1L);
        ConfigurationFile configurationFile2 = new ConfigurationFile();
        configurationFile2.setId(configurationFile1.getId());
        assertThat(configurationFile1).isEqualTo(configurationFile2);
        configurationFile2.setId(2L);
        assertThat(configurationFile1).isNotEqualTo(configurationFile2);
        configurationFile1.setId(null);
        assertThat(configurationFile1).isNotEqualTo(configurationFile2);
    }
}
