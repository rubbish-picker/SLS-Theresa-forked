package Theresa.card.attack;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Withering extends AbstractTheresaCard {
    public static final String ID = "theresa:Withering";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Withering() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 3;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int randomNumber = AbstractDungeon.cardRandomRng.random(0, this.damage*2);
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,randomNumber,damageTypeForTurn),attackEffect));
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new DyingPower(abstractMonster,magicNumber),magicNumber));
    }

    @Override
    public void triggerWhenBecomeDust() {
        this.flash();
        int randomNumber = AbstractDungeon.cardRandomRng.random(0, this.magicNumber*2);
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDeadOrEscaped()){
                addToBot(new ApplyPowerAction(mo,AbstractDungeon.player,new DyingPower(mo,randomNumber),randomNumber));
            }
        }
    }

    @Override
    public void triggerWhenNoLongerDust() {
        this.flash();
        int randomNumber = AbstractDungeon.cardRandomRng.random(0, this.magicNumber*2);
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDeadOrEscaped()){
                addToBot(new ApplyPowerAction(mo,AbstractDungeon.player,new DyingPower(mo,randomNumber),randomNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(2);
        }
    }
}
