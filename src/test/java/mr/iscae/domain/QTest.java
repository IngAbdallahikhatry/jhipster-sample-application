package mr.iscae.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mr.iscae.web.rest.TestUtil;

public class QTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Q.class);
        Q q1 = new Q();
        q1.setId(1L);
        Q q2 = new Q();
        q2.setId(q1.getId());
        assertThat(q1).isEqualTo(q2);
        q2.setId(2L);
        assertThat(q1).isNotEqualTo(q2);
        q1.setId(null);
        assertThat(q1).isNotEqualTo(q2);
    }
}
