package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefence = "";

    public static int[] heroesHealth = {270, 260, 250, 300,400,200,200,150};
    public static int[] heroesDamage = {15, 20, 25, 0, 5,15,20,10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic","Golem","Luky","Berserk","Thour"};
    public static int round_number = 0;

    public static void main(String[] args) {
        printStatistics(); // До начала игры вывод статистики
        while (!isGameFinished()) {
            round();
        }
    }

    public static void changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); //0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose " + bossDefence);
    }

    public static void round() {
        round_number++;
        changeBossDefence();
        bossHits();
        medicSkill();
        golemSkill();
        missPnch();
        berserk();
        thour();
        heroesHit();
        printStatistics();
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            Random random = new Random();
            int coeff = random.nextInt(8) + 2;
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void medicSkill() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3) {
                continue;
            }

            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                heroesHealth[i] += 50;
                System.out.println("Medic hill +++" + heroesAttackType[i]);
                break;
            }
        }
    }


    public static void golemSkill() {
        int dmg = bossDamage / 5;
        int howAliveHerro = 0;
        for (int i = 0; i < heroesDamage.length; i++) {
            if (i == 4) {
                continue;
            }
            else if (heroesHealth[i] > 0 && heroesHealth[4]>0){
                howAliveHerro++;
                heroesHealth[i]  += dmg;
            }
        }
        heroesHealth[4] -= dmg * howAliveHerro;
        System.out.println("Голем получает урон:" + dmg * howAliveHerro);
    }

    public static void missPnch(){
        Random random = new Random();
        boolean miss = random.nextBoolean();
        if (heroesHealth[5]>0 && miss){
            heroesHealth[5]+=bossDamage-bossDamage/5;
            System.out.println("miss punch:"+miss);
        }
    }
    public static void berserk(){
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[6]>0){
                heroesHealth[6]+=bossDamage/5*2;
                bossHealth-=bossDamage/5*2;
                break;
            }
        }
    }

    public static void thour(){
        Random random=new Random();
        boolean stn = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[7] > 0) {
                if (stn){
                    bossDamage=0;
                    System.out.println("тор дал стан"+stn);
                    break;}
            }else{
                bossDamage=50;
                break;
            }
        }
    }
    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    if(heroesDamage[i] == 3){
                        System.out.println("Critical hill " + heroesDamage[i] * coeff);
                    }else{
                        System.out.println("Critical damage " + heroesDamage[i] * coeff);
                    }
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND _______________");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
        System.out.println("_______________");
    }
}