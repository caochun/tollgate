from miio import airpurifier, exceptions


def main():
    airpurifier.AirPurifier(ip="192.168.80.148", token="acf8b3ced11c488f447b5a662ff3cd95")
    print(airpurifier.status())



if __name__ == '__main__':
    main()
