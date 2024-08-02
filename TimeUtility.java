package zacarias.desktopSchedule.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

/**
 * The type Time utility.
 */
public class TimeUtility {
    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();
    private static final ZonedDateTime EASTERN_START_TIME = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));

    private static void initializeTimeLists(){
        LocalDateTime localStart = EASTERN_START_TIME.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localEnd = localStart.plusHours(14);

        while (localStart.isBefore(localEnd)) {
            startTimes.add(localStart.toLocalTime());
            localStart = localStart.plusMinutes(30);
            endTimes.add(localStart.toLocalTime());
        }
    }

    /**
     * Gets start times.
     *
     * @return start times
     */
    public static ObservableList<LocalTime> getStartTimes() {
        if (startTimes.isEmpty()) {
            initializeTimeLists();
        }
        return startTimes;
    }

    /**
     * Gets end times.
     *
     * @return end times
     */
    public static ObservableList<LocalTime> getEndTimes() {
        if (endTimes.isEmpty()) {
            initializeTimeLists();
        }
        return endTimes;
    }

    /**
     * Get local start local time.
     *
     * @return local time
     */
    public static LocalTime getLocalStart(){
        LocalTime localStartEst = LocalTime.of(8,0);
        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.systemDefault();

        LocalDateTime busEstDT = LocalDateTime.of(LocalDate.now(), localStartEst);
        LocalDateTime busLocalDT = busEstDT.atZone(estZone).withZoneSameInstant(localZone).toLocalDateTime();

        LocalTime busStartLocal = busLocalDT.toLocalTime();
        return busStartLocal;

    }

    /**
     * Get local end local time.
     *
     * @return local time
     */
    public static LocalTime getLocalEnd(){
        LocalTime localEndEst = LocalTime.of(22,0);
        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.systemDefault();

        LocalDateTime busEstDT = LocalDateTime.of(LocalDate.now(), localEndEst);
        LocalDateTime busLocalDT = busEstDT.atZone(estZone).withZoneSameInstant(localZone).toLocalDateTime();

        LocalTime busEndLocal = busLocalDT.toLocalTime();
        return busEndLocal;
    }

    /**
     * Check same day boolean.
     *
     * @param appointmentStart the appointment start
     * @param appointmentEnd   the appointment end
     * @return boolean
     */
    public static boolean checkSameDay(LocalDateTime appointmentStart, LocalDateTime appointmentEnd){
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");

        LocalDateTime appointmentStartEst = appointmentStart.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        LocalDateTime appointmentEndEst = appointmentEnd.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();

        LocalDate appointmentStartEstDate = appointmentStartEst.toLocalDate();
        LocalDate appointmentEndEstDate = appointmentEndEst.toLocalDate();

        if(!(appointmentStartEstDate.isEqual(appointmentEndEstDate))){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Outside local hours boolean.
     *
     * @param appointmentStart the appointment start
     * @param appointmentEnd   the appointment end
     * @return boolean
     */
    public static boolean outsideLocalHours(LocalDateTime appointmentStart, LocalDateTime appointmentEnd){
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");

        LocalDateTime appointmentStartEst = appointmentStart.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        LocalDateTime appointmentEndEst = appointmentEnd.atZone(localZone).withZoneSameInstant(estZone).toLocalDateTime();
        LocalDateTime localStartEst = appointmentStartEst.withHour(8).withMinute(0);
        LocalDateTime localEndEst = appointmentEndEst.withHour(22).withMinute(0);

        if(appointmentStartEst.isBefore(localStartEst) || appointmentEndEst.isAfter(localEndEst)){
            return true;
        }
        else{
            return false;
        }
    }
}