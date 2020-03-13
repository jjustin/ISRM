[Navodila](http://lrkucilnica.fri.uni-lj.si/T01/wireshark.html)

S programom Wireshark odprite datoteko L1_3.cap. Preučite pakete in odgovorite na spodnja vprašanja:

1. Za kateri protokol gre pri prikazanih paketih?  
   ICMP
2. Pri prikazanem protokolu vidimo, da se izmenjujeta 2 tipa sporočil. Za kateri sporočili gre?  
   Ping request/reply
3. Kdo je pošiljatelj paketa št. 1? Kdo pa prejemnik?  
   88.200.24.4 je posilatelj. Prejemnik pa je 193.2.1.66
4. Kdo je pošiljatelj paketa št. 2? Kdo pa prejemnik?  
   193.2.1.66 je posilatelj. Prejemnik pa je 88.200.24.4

V zgornjih nalogah ste uporabljali promet iz datoteke, ki smo jo za vas predhodno pripravili. V programu Wireshark sprožite zajem prometa in odgovorite na spodnja vprašanja:

1. Kakšen filter morate napisati, če želite videti samo pakete, ki jih pošilja in sprejema program ping?  
   Filter: ICMP ali ICMPv6
2. Kakšne podatke pošilja program ping proti prejemniku? Kako dolg je posamezni paket (upoštevajte celotno dolžino paketa)?  
   Data je nastavljena v naprej(48(ipv4) ali 64(ipv6) bajtov).
   Dolg je 98 bajtov.

# Dodatno

## Filter

| tip    | ping request | reply    |
| ------ | ------------ | -------- |
| icmpv6 | type=128     | type=129 |
| icmp   | type=8       | type=0   |

```
icmpv6.type==128 or icmpv6.type==129 //prikaze samo requeste in replyje za ping6
ip.src==192... //pokaze samo za dolocen ip
```

## Enkapsulacija

- [Ethernet:
  - (Polje za podatke)
  - [IPv4:
    - (Polje za podatke)
    - [TCP:
      - (Polje za podatke)
      - ...
    - ]
  - ]
- ]
