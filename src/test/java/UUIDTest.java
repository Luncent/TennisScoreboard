import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {
    @Test
    public void uuidCompareTest(){
        UUID uid = UUID.randomUUID();
        UUID newUUID = UUID.fromString(uid.toString());
        UUID uid2 = UUID.randomUUID();
        System.out.println(uid.toString());
        System.out.println(uid2.toString());
        System.out.println(newUUID.compareTo(uid));
        System.out.println(newUUID.compareTo(uid2));
    }
}
