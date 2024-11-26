import 'package:flutter/material.dart';

class MainPage extends StatefulWidget {
  const MainPage({super.key});

  @override
  State<MainPage> createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
    static List<Widget> _pageOptions = const <Widget>[
    
  ];

  int _pageIndex = 0; 


 
  void onPageSelected(int index) {
    setState(() {
      _pageIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pageOptions.elementAt(_pageIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Image.asset(
              'assets/icons/home.png',
              height: 21.37,
            ),
            label: 'Главная',
          ),
          BottomNavigationBarItem(
            icon: Image.asset(
              'assets/icons/user.png',
              height: 21,
            ),
            label: 'Профиль',
          ),
        ],
        currentIndex: _pageIndex,
        onTap: onPageSelected,
      ),
    );
  }
}