package com.mike.projectboxscore;

import com.mike.projectboxscore.console.MainConsolePresenter;
import com.mike.projectboxscore.console.MainConsoleViewContract;
import com.mike.projectboxscore.datas.Game;
import com.mike.projectboxscore.datas.PlayerStats;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleActionTest {

    private MainConsoleViewContract.Presenter mConsolePresenter;

    @Mock
    MainConsoleViewContract.View view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Game game = new Game();
        PlayerStats playerStats = new PlayerStats();
        game.addmPlayerStat(playerStats);
        mConsolePresenter = new MainConsolePresenter(view, game);
        mConsolePresenter.setSelectedPlayer(playerStats);
    }

    @Test
    public void playerMadeTenShot() {
        //five two points shot
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);

        //five three points shot
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);

        assertEquals(10, mConsolePresenter.getSelectedPlayer().getShotMade());
    }
    @Test
    public void checkTwoPointsPercentage() {
        //one made shot
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        //two missed shot
        mConsolePresenter.updatePlayerMisses(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.TWO_POINT);
        assertEquals(33.3, mConsolePresenter.getSelectedPlayer().getShotPercentage(), 0);
    }

    @Test
    public void checkTwoPointsPercentageReturned() {
        //made three shot
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);

        //missed four shot
        mConsolePresenter.updatePlayerMisses(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.TWO_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.TWO_POINT);

        //return two missed shot
        mConsolePresenter.updatePlayerMisses(Constants.RETURN_TWO_POINTS);
        mConsolePresenter.updatePlayerMisses(Constants.RETURN_TWO_POINTS);

        //return two made shot
        mConsolePresenter.updatePlayerScores(Constants.RETURN_TWO_POINTS);
        mConsolePresenter.updatePlayerScores(Constants.RETURN_TWO_POINTS);

        assertEquals(33.3, mConsolePresenter.getSelectedPlayer().getShotPercentage(), 0);
    }

    @Test
    public void checkThreePointsPercentage() {
        //one made shot
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        //two missed shot
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        assertEquals(33.3, mConsolePresenter.getSelectedPlayer().getShotPercentage(), 0);
    }

    @Test
    public void checkThreePointsPercentageReturned() {
        //made three shot
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);

        //missed four shot
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);

        //return two missed shot
        mConsolePresenter.updatePlayerMisses(Constants.RETURN_THREE_POINTS);
        mConsolePresenter.updatePlayerMisses(Constants.RETURN_THREE_POINTS);

        //return two made shot
        mConsolePresenter.updatePlayerScores(Constants.RETURN_THREE_POINTS);
        mConsolePresenter.updatePlayerScores(Constants.RETURN_THREE_POINTS);

        assertEquals(33.3, mConsolePresenter.getSelectedPlayer().getShotPercentage(), 0);
    }

    @Test
    public void checkTenRebound() {
        mConsolePresenter.playerOffensiveRebounded(5);
        mConsolePresenter.playerDefensiveRebounded(5);
        assertEquals(10, mConsolePresenter.getSelectedPlayer().getRebounds());
    }

    @Test
    public void checkReboundReturn() {
        //made five offensive rebound
        mConsolePresenter.playerOffensiveRebounded(Constants.ONE);
        mConsolePresenter.playerOffensiveRebounded(Constants.ONE);
        mConsolePresenter.playerOffensiveRebounded(Constants.ONE);
        mConsolePresenter.playerOffensiveRebounded(Constants.ONE);
        mConsolePresenter.playerOffensiveRebounded(Constants.ONE);

        //made five defensive rebound
        mConsolePresenter.playerDefensiveRebounded(Constants.ONE);
        mConsolePresenter.playerDefensiveRebounded(Constants.ONE);
        mConsolePresenter.playerDefensiveRebounded(Constants.ONE);
        mConsolePresenter.playerDefensiveRebounded(Constants.ONE);
        mConsolePresenter.playerDefensiveRebounded(Constants.ONE);

        //return 3 defensive rebound
        mConsolePresenter.playerDefensiveRebounded(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerDefensiveRebounded(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerDefensiveRebounded(Constants.RETURN_ONE_STAT);

        //return 3 offensive rebound
        mConsolePresenter.playerOffensiveRebounded(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerOffensiveRebounded(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerOffensiveRebounded(Constants.RETURN_ONE_STAT);

        assertEquals(2, mConsolePresenter.getSelectedPlayer().getOffensiveRebounds());
        assertEquals(2, mConsolePresenter.getSelectedPlayer().getDefensiveRebounds());
        assertEquals(4, mConsolePresenter.getSelectedPlayer().getRebounds());
    }

    @Test
    public void checkSteal() {
        //made six steals
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);

        assertEquals(6, mConsolePresenter.getSelectedPlayer().getSteals());
    }

    @Test
    public void checkStealReturn() {
        //made six steals
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);
        mConsolePresenter.playerStealed(Constants.ONE);

        //returned two steals
        mConsolePresenter.playerStealed(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerStealed(Constants.RETURN_ONE_STAT);

        assertEquals(4, mConsolePresenter.getSelectedPlayer().getSteals());
    }

    @Test
    public void checkAssist() {
        //made six steals
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);

        assertEquals(6, mConsolePresenter.getSelectedPlayer().getAssists());
    }

    @Test
    public void checkAssistReturn() {
        //made six steals
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);
        mConsolePresenter.playerAssisted(Constants.ONE);

        //returned two steals
        mConsolePresenter.playerAssisted(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerAssisted(Constants.RETURN_ONE_STAT);

        assertEquals(4, mConsolePresenter.getSelectedPlayer().getAssists());
    }

    @Test
    public void checkBlock() {
        //made six steals
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);

        assertEquals(6, mConsolePresenter.getSelectedPlayer().getBlocks());
    }

    @Test
    public void checkBlockReturn() {
        //made six steals
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);
        mConsolePresenter.playerBlocked(Constants.ONE);

        //returned two steals
        mConsolePresenter.playerBlocked(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerBlocked(Constants.RETURN_ONE_STAT);

        assertEquals(4, mConsolePresenter.getSelectedPlayer().getBlocks());
    }

    @Test
    public void checkTurnOver() {
        //made six steals
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);

        assertEquals(6, mConsolePresenter.getSelectedPlayer().getTurnOvers());
    }

    @Test
    public void checkTurnOverReturn() {
        //made six steals
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);
        mConsolePresenter.playerTurnedOver(Constants.ONE);

        //returned two steals
        mConsolePresenter.playerTurnedOver(Constants.RETURN_ONE_STAT);
        mConsolePresenter.playerTurnedOver(Constants.RETURN_ONE_STAT);

        assertEquals(4, mConsolePresenter.getSelectedPlayer().getTurnOvers());
    }
}




























