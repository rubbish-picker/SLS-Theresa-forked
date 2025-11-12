package Theresa.action;

import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ToughHistoryAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse;
    boolean upgraded;

    public ToughHistoryAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse,boolean upgraded) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            if(upgraded){
                for(int i = 0; i < effect; ++i) {
                    addToTop(new TransformAction(p));
                }
            }
            for(int i = 0; i < effect; ++i) {
                addToTop(new ApplyPowerAction(p,p,new HatePower(p,1),1));
                addToTop(new ApplyPowerAction(p,p,new HopePower(p,1),1));
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}



