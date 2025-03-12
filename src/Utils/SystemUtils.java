package Utils;

public class SystemUtils {
    public static void logoutAnimation() {
        try {
            for (int i = 0; i < 3; i++) { // Lägger till tre punkter med fördröjning
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nDu har blivit utloggad.");
    }

    public static void tillbakaAnimation(String menyNamn) {
        System.out.print("Går tillbaka till " + menyNamn);
        try {
            for (int i = 0; i < 3; i++) { // Lägger till tre punkter med fördröjning
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


    }

    public static void avslutarAnimation() {
        try {
            for (int i = 0; i < 3; i++) { // Lägger till tre punkter med fördröjning
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nProgrammet avslutat.");
    }
}
