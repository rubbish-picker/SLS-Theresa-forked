package Theresa.card.attack;

import Theresa.action.DyingBurstAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.power.debuff.DyingBurstPower;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class GoodNight extends AbstractTheresaCard {
    public static final String ID = "theresa:GoodNight";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public GoodNight() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 5;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        if(!isInAutoplay){
            monsters.add(abstractMonster);
        }
        else {
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!m.isDeadOrEscaped())
                    monsters.add(m);
            }
        }
        for(AbstractMonster m : monsters){
            if(isInAutoplay)
                calculateCardDamage(m);
            int randomNumber = com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, this.damage*2);
            addToBot(new DamageAction(m,new DamageInfo(abstractPlayer,randomNumber,damageTypeForTurn),skillEffect));
            addToBot(new ApplyPowerAction(m,abstractPlayer,new DyingPower(m,magicNumber),magicNumber));
            //addToBot(new DyingBurstAction(abstractPlayer,abstractMonster));
            addToBot(new ApplyPowerAction(m,abstractPlayer,new DyingBurstPower(m,1),1));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(2);
        }
    }
}


