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
        for (int i = 0; i < 5; i++) {
            mConsolePresenter.updatePlayerScores(Constants.TWO_POINT);
        }
        //five three points shot
        for (int i = 0; i < 5; i++) {
            mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        }
        assertEquals(10, mConsolePresenter.getSelectedPlayer().getShotMade());
    }

    @Test
    public void checkThreePointsPercentage() {
        mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        assertEquals(33.3, mConsolePresenter.getSelectedPlayer().getShotPercentage(), 0);
    }

    @Test
    public void checkThreePointsPercentageReturned() {
        //made one shot
        for (int i = 0; i < 1; i++) {
            mConsolePresenter.updatePlayerScores(Constants.THREE_POINT);
        }
        //missed four shot
        for (int i = 0; i < 4; i++) {
            mConsolePresenter.updatePlayerMisses(Constants.THREE_POINT);
        }
        //return two shot
        for (int i = 0; i < 2; i++) {
            mConsolePresenter.updatePlayerMisses(Constants.RETURN_THREE_POINTS);
        }
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
        for (int i = 0; i < 5; i++) {
            mConsolePresenter.playerOffensiveRebounded(Constants.ONE);
        }
        //made five defensive rebound
        for (int i = 0; i < 5; i++) {
            mConsolePresenter.playerDefensiveRebounded(Constants.ONE);
        }
        //return 3 defensive rebound
        for (int i = 0; i < 3; i++) {
            mConsolePresenter.playerDefensiveRebounded(Constants.RETURN_ONE_STAT);
        }
        //return 3 offensive rebound
        for (int i = 0; i < 3; i++) {
            mConsolePresenter.playerOffensiveRebounded(Constants.RETURN_ONE_STAT);
        }
        assertEquals(2, mConsolePresenter.getSelectedPlayer().getOffensiveRebounds());
        assertEquals(2, mConsolePresenter.getSelectedPlayer().getDefensiveRebounds());
        assertEquals(4, mConsolePresenter.getSelectedPlayer().getRebounds());
    }

    @Test
    public void checkSteal() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerStealed(Constants.ONE);
        }
        assertEquals(6, mConsolePresenter.getSelectedPlayer().getSteals());
    }

    @Test
    public void checkStealReturn() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerStealed(Constants.ONE);
        }
        for (int i = 0; i < 2; i++) {
            mConsolePresenter.playerStealed(Constants.RETURN_ONE_STAT);
        }
        assertEquals(4, mConsolePresenter.getSelectedPlayer().getSteals());
    }

    @Test
    public void checkAssist() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerAssisted(Constants.ONE);
        }
        assertEquals(6, mConsolePresenter.getSelectedPlayer().getAssists());
    }

    @Test
    public void checkAssistReturn() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerAssisted(Constants.ONE);
        }
        for (int i = 0; i < 2; i++) {
            mConsolePresenter.playerAssisted(Constants.RETURN_ONE_STAT);
        }
        assertEquals(4, mConsolePresenter.getSelectedPlayer().getAssists());
    }

    @Test
    public void checkBlock() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerBlocked(Constants.ONE);
        }
        assertEquals(6, mConsolePresenter.getSelectedPlayer().getBlocks());
    }

    @Test
    public void checkBlockReturn() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerBlocked(Constants.ONE);
        }
        for (int i = 0; i < 2; i++) {
            mConsolePresenter.playerBlocked(Constants.RETURN_ONE_STAT);
        }
        assertEquals(4, mConsolePresenter.getSelectedPlayer().getBlocks());
    }

    @Test
    public void checkTurnOver() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerTurnedOver(Constants.ONE);
        }
        assertEquals(6, mConsolePresenter.getSelectedPlayer().getTurnOvers());
    }

    @Test
    public void checkTurnOverReturn() {
        for (int i = 0; i < 6; i++) {
            mConsolePresenter.playerTurnedOver(Constants.ONE);
        }
        for (int i = 0; i < 2; i++) {
            mConsolePresenter.playerTurnedOver(Constants.RETURN_ONE_STAT);
        }
        assertEquals(4, mConsolePresenter.getSelectedPlayer().getTurnOvers());
    }
}




























