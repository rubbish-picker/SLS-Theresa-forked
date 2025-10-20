package Theresa.action;

import Theresa.helper.StringHelper;
import Theresa.helper.TheresaHelper;
import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TowardEndfieldAction extends AbstractGameAction {
    private AbstractPlayer p;

    public TowardEndfieldAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(null, null, amount);
        this.actionType = ActionType.DISCARD;
        this.duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if(duration == startDuration) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                int tmp = this.p.hand.size();

                for(int i = 0; i < tmp; ++i) {
                    AbstractCard c = this.p.hand.getTopCard();
                    if(TheresaHelper.canBecomeDust(c)){
                        if(p.hoveredCard == c)
                            this.p.releaseCard();
                        this.p.hand.removeCard(c);
                        DustPatch.dustManager.addCard(c);
                    }
                    else {
                        this.p.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                    }
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            if (this.amount < 0) {
                AbstractDungeon.handCardSelectScreen.open(StringHelper.OPERATION.TEXT[9], 99, true, true);
                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            if (this.p.hand.size() > this.amount) {
                AbstractDungeon.handCardSelectScreen.open(StringHelper.OPERATION.TEXT[9], this.amount, false);
            }

            AbstractDungeon.player.hand.applyPowers();

        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if(TheresaHelper.canBecomeDust(c)){
                    if(p.hoveredCard == c)
                        this.p.releaseCard();
                    this.p.hand.removeCard(c);
                    DustPatch.dustManager.addCard(c);
                }
                else {
                    this.p.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }
}
