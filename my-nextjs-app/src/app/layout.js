import "../styles/globals.css";
import localFont from 'next/font/local';


const myFont = localFont({
  src: [
    {
      path: '../../public/fonts/Involve-Regular.ttf',
      weight: '400',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Involve-Bold.ttf',
      weight: '700',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Involve-SemiBold.ttf',
      weight: '600',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Involve-Medium.ttf',
      weight: '500',
      style: 'normal',
    },
  ],
  display: 'swap',
}); 

export default function RootLayout({ children }) {
  return (
    <html lang="ru">
      <body className={`bg-customColor1 ${myFont.className}`}>
        {children}
      </body>
    </html>
  );
}
