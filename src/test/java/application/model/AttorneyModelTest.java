package application.model;

import io.swagger.model.Attorney;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AttorneyModelTest {
    private static final List<String> attorneys = Arrays.asList("fred");
    private static AttorneyModel attorneyModel;

    @BeforeClass
    public static void beforeClass() {
        attorneyModel = new AttorneyModel("http://admin:password@localhost:5984", null, "outcarcerate-attorney");
    }

    @AfterClass
    public static void afterClass() {
        for (String attorney: attorneys) {
            attorneyModel.delete(attorney);
        }
    }

    @Test
    public void save() {
        Attorney fred = new Attorney();
        fred.setUsername("fred");
        attorneyModel.save(fred);
        attorneyModel.save(fred);
        Assert.assertEquals(1, attorneyModel.getAll().getDocs().size());
    }

    @Test
    public void delete() {
    }

    @Test
    public void read() {
    }

    @Test
    public void addCaseToAttorney() {
    }

    @Test
    public void deleteCaseFromAttorney() {
    }

    @Test
    public void getCasesForAttorney() {
    }

    @Test
    public void getCasesByIdForAttorney() {
    }

    @Test
    public void getAll() {
    }
}