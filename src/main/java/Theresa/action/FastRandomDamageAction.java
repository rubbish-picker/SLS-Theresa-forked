package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FastRandomDamageAction extends AbstractGameAction {
    private DamageInfo info;

    public FastRandomDamageAction(DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect,true));
        }

        this.isDone = true;
    }
}
