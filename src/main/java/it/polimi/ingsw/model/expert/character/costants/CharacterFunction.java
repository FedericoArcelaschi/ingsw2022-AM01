package it.polimi.ingsw.model.expert.character.costants;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffect;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffectConquer;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffectInfluence;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffectStudents;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import it.polimi.ingsw.model.influence.Influence;
import it.polimi.ingsw.model.expert.influence.InfluenceComputingFunction;
import it.polimi.ingsw.model.expert.influence.professor.ProfessorsMapComputingFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

enum CharacterFunction {
    MONK(
        (List<Color> requestedStudents, List<Color> availableStudents, Bag bag, List<StudentPlaces> placesList)
        -> {
            Color student = requestedStudents.get(0);
            if (student == null)
                throw new IllegalArgumentException("no student in monk");
            StudentPlaces island = placesList.get(2);
            if (island == null)
                throw new IllegalArgumentException("no island in monk");
            if (!availableStudents.contains(student))
                throw new NoSuchStudentException("Students not available on monk card");
            island.adds(student);//Adds one student per use.
            availableStudents.remove(student);
            availableStudents.add(bag.extract());
        }
    ),
    FARMER(
        (List<Color> students, List<StudentPlaces> placesList, @NotNull Influence influence, IntegerBoxing steps)
        -> {
            influence.setProfessorFunction(ProfessorsMapComputingFunction.FARMER.getFunction());
            ExpertCastle currPlayerCastle = (ExpertCastle) placesList.get(0);
            influence.setCurrTeam(currPlayerCastle.getTeam());
        }
    ),

    GUARD() {//FIXME
       ApplyEffect a
               = (List<ExpertIsland> islandList, Influence influence, Integer islandIndex, ApplyEffectConquer functionConquer)
               -> (functionConquer =
                        (islandList, influence, islandIndex)
                            -> {
                        ExpertIsland island = islandList.get(islandIndex);
                        Team teamBeforeComputing = island.getOwnership();
                        Team t = it.polimi.ingsw.model.functionalnterfaces.GreaterTeam.findGreaterTeam(influence.getInfluenceMap(island));
                        if (t == null || t.equals(teamBeforeComputing))//no island is conquered.
                            return;
                        island = island.setOwnership(t);
                        ExpertIsland previous, next;
                        if (islandIndex == 0) {
                            previous = islandList.get(islandList.size() - 1);
                            next = islandList.get(1);
                        } else if (islandIndex == islandList.size() - 1) {
                            previous = islandList.get(islandList.size() - 2);
                            next = islandList.get(0);
                        } else {
                            previous = islandList.get(islandIndex - 1);
                            next = islandList.get(islandIndex + 1);
                        }

                        List<ExpertIsland> neightbouringIsland = Arrays.asList(previous, island, next);

                        List<ExpertIsland> islandToJoin = null;
                        if (neightbouringIsland.get(0).getOwnership() != null) {
                            if (neightbouringIsland.get(0).getOwnership() == neightbouringIsland.get(1).getOwnership())
                                islandToJoin = neightbouringIsland.subList(0, 2);
                            if (neightbouringIsland.get(1).getOwnership() == neightbouringIsland.get(2).getOwnership())
                                islandToJoin.add(neightbouringIsland.get(2));
                        } else if (neightbouringIsland.get(1).getOwnership() == neightbouringIsland.get(2).getOwnership())//not the first one for sure.
                            islandToJoin = neightbouringIsland.subList(1, 3);
                        if (islandToJoin == null)
                            return; //another escape if the island won't join
                        int firstIslandIndex
                                = islandList
                                .indexOf(islandToJoin.get(0));
                        if (firstIslandIndex == -1)
                            throw new IllegalArgumentException("island: " + islandToJoin.get(0).toString() + "not found!");
                        Archipelago newArchipelago = null;
                        if (islandToJoin.size() == 2) {
                            if (islandToJoin.removeAll(islandToJoin))
                                newArchipelago = new Archipelago(islandToJoin.get(0), islandToJoin.get(1));
                        } else if (islandToJoin.size() == 3) {
                            if (islandToJoin.removeAll(islandToJoin))
                                newArchipelago = new Archipelago(islandToJoin.get(0), islandToJoin.get(1), islandToJoin.get(2));
                            else
                                throw new IllegalStateException();
                        } else
                            throw new IllegalArgumentException("wrong number of islands in the given list: " + islandList);
                        ExpertIsland newExpIsland = new ExpertIsland(newArchipelago);
                        islandList.add(firstIslandIndex, newExpIsland);
                    });

        @Override
        public ApplyEffect getFunction() {
            return a;
        }
    },
    MAILMAN((
            List<Color> students, List<StudentPlaces> placesList, ExpertInfluence influence, IntegerBoxing steps)
        -> {
            if (steps == null)
                throw new IllegalArgumentException("MailMan: needed input");
            int i = steps.getInt();
            steps.setInt(i+2);
        }
    ),
    WITCH(),
    CENTAUR(
        (List<Color> students, List<StudentPlaces> placesList, ExpertInfluence influence, Integer steps)
        ->  influence.changeFunction(InfluenceComputingFunction.CENTAUR.getFunction())
    ),
    JESTER(),
    KNIGHT(
        (List<Color> students, List<StudentPlaces> placesList, ExpertInfluence influence, Integer steps)
        -> {
            influence.changeFunction(InfluenceComputingFunction.KNIGHT.getFunction());
            ExpertCastle ec = (ExpertCastle) placesList.get(0);
            influence.setCurrTeam(ec.getTeam());
        }
    ),
    COOK(
        (List<Color> students, List<StudentPlaces> placesList, ExpertInfluence influence, Integer steps)
        ->{
            Color student = students.get(0);
            if (student == null)
                throw new IllegalArgumentException("no student given for cook card");
            if (influence == null)
                throw new IllegalAccessException("influence problem (model-side)");
            influence.setColorToIgnore(student);
        }
    ),
    STORYTELLER (
        (List<Color> requestedStudents, List<Color>availableStudents, Bag bag, List<StudentPlaces> placesList)
        -> {
            if (requestedStudents.size() != 4)
                throw new IllegalArgumentException("wrong input list.");
            //TODO
        }
    ),
    QUEEN(),
    TAXMAN();


    public static CharacterFunction getInstance(int idChar){
        return CharacterFunction.values()[idChar-1];
    }

    private ApplyEffect function;
    private ApplyEffectStudents function;
    private ApplyEffectConquer function;
    private ApplyEffectInfluence function;

    CharacterFunction(ApplyEffectStudents function) {
        this.function = function;
    }
    CharacterFunction(ApplyEffectInfluence function) {
        this.function = function;
    }
    CharacterFunction(ApplyEffectConquer function) {
        this.function = function;
    }
    CharacterFunction(ApplyEffect function) {
        this.function = function;
    }

    @Contract(pure = true)
    public ApplyEffect getFunction(){
        return this.function;
    }
}
