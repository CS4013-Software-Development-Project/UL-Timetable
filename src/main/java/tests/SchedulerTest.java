package tests;

import model.grouping.Subgroup;
import model.module.Module;
import model.module.Programme;
import model.room.Room;
import model.room.RoomType;
import model.schedule.Scheduler;
import model.user.Leader;
import model.user.Student;
import persistence.PersistenceManager;

import org.junit.Test;
import util.SaveUtil;

public class SchedulerTest {

    @Test
    public void testPopulateRooms() {
        Scheduler.populateRoomTimeslots();
    }
}
