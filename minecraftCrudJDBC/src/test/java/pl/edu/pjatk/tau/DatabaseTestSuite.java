package pl.edu.pjatk.tau;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import pl.edu.pjatk.tau.dao.PlayerDaoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(PlayerDaoTest.class)
public class DatabaseTestSuite {
}