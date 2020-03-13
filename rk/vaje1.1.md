[Navodila](http://lrkucilnica.fri.uni-lj.si/T01/ping.html)

- Preverite dosegljivost naslova 88.200.24.1. Kateri ukaz ste uporabili na Windows? Kaj pa na Linuxu?

```shell
$ ping 88.200.24.1
PING 88.200.24.1 (88.200.24.1): 56 data bytes
64 bytes from 88.200.24.1: icmp_seq=0 ttl=61 time=2.136 ms
64 bytes from 88.200.24.1: icmp_seq=1 ttl=61 time=1.232 ms
64 bytes from 88.200.24.1: icmp_seq=2 ttl=61 time=2.042 ms
64 bytes from 88.200.24.1: icmp_seq=3 ttl=61 time=2.083 ms
64 bytes from 88.200.24.1: icmp_seq=4 ttl=61 time=2.180 ms
```

- Preverite dosegljivost naslova 2001:1470:fffd::1. Kateri ukaz ste uporabili na Windows? Kaj pa na Linuxu?

```shell
$ ping6 2001:1470:fffd::1
PING6(56=40+8+8 bytes) 2001:1470:ffef:fe01:311f:2ad3:169b:557b --> 2001:1470:fffd::1
16 bytes from 2001:1470:fffd::1, icmp_seq=0 hlim=62 time=14.261 ms
16 bytes from 2001:1470:fffd::1, icmp_seq=1 hlim=62 time=3.147 ms
16 bytes from 2001:1470:fffd::1, icmp_seq=2 hlim=62 time=7.845 ms
16 bytes from 2001:1470:fffd::1, icmp_seq=3 hlim=62 time=9.647 ms
16 bytes from 2001:1470:fffd::1, icmp_seq=4 hlim=62 time=3.735 ms
16 bytes from 2001:1470:fffd::1, icmp_seq=5 hlim=62 time=7.995 ms
16 bytes from 2001:1470:fffd::1, icmp_seq=6 hlim=62 time=3.089 ms
16 bytes from 2001:1470:fffd::1, icmp_seq=7 hlim=62 time=3.110 ms
```

Na windwosu bi uporabili `ping -6 2001:1470:fffd::1`

- Za vsakega od spodnjih naslovov napišite, ali je bil v času vašega preizkušanja dosegljiv ali ne:

  - 193.2.1.66  
    Dosegljiv
  - www.fri.uni-lj.si  
    Dosegljiv
  - ipv6.google.com  
    Dosegljiv preko `ping6`
  - www.rtvslo.si  
    Nedosegljiv
  - www.berkeley.edu  
    Dosegljiv

- Naslove iz prejšnjega vprašanja, na katere s programom ping niste dobili odgovora, preizkusite še s spletnim brskalnikom. Ali sedaj dobite odgovor? Zakaj?  
  Preko brskalnika je dostopen. Na rtvslo je firewall, ki blokira pinge (blokira ICMP\ICMPv6 pakete).
- Najbrž ste opazili, da se program ping na Windows obnaša nekoliko drugače kot na Linuxu. Kako lahko ping na Linuxu prepričate, da se bo obnašal tako kot tisti na Windows?(Da ne pinga v nedogled)  
  Z uporabo `-c` flaga. Npr. `ping -c 4 192.168.1.1`
